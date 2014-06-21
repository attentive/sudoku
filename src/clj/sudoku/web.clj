(ns sudoku.web
  (:use compojure.core
        ring.util.response
        [ring.middleware edn json-params file file-info session stacktrace reload])
  (:require compojure.handler
            [clojure.string :as string]
            [taoensso.timbre :as timbre]
            [chord.http-kit :refer [wrap-websocket-handler]]
            [clojure.core.async :refer [<! >! put! close! go-loop]]
            [sudoku.page :as page]))

(timbre/refer-timbre)

(defn wrap-echo-socket-handler [path]
  (fn [{:keys [ws-channel] :as req}]
    (let [topic (into [] (map keyword (string/split path #"/")))]
      (info (str "Binding echo-socket-handler on " topic))
      (go-loop []
               (when-let [{:keys [message error] :as msg} (<! ws-channel)]
                 (info (str "Rx " topic ": " message))
                 (>! ws-channel 
                     (if error
                       (format "Error: '%s'." (pr-str msg))
                       message)))
               (recur)))))

(defroutes sudoku-routes
  ; Main page
  (GET "/" [] (response (page/main)))
  (GET "/socket/:path" req 
       (-> (wrap-echo-socket-handler (:path (:route-params req))) 
           (wrap-websocket-handler {:format :json-kw}))))

(def sudoku-app
  (-> sudoku-routes
      ; Need to use "api", not "site" handler to avoid clobbering session by 
      ; double application of wrap-session middleware
      compojure.handler/api
      (wrap-json-params)
      (wrap-edn-params)
      (wrap-file "resources/public")
      (wrap-file-info)))


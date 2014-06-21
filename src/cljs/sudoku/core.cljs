(ns sudoku.core
  (:require-macros [cljs.core.async.macros :refer [go go-loop]])
  (:require [om.core :as om :include-macros true]
            [om.dom :as dom :include-macros true]
            [sablono.core :as html :refer-macros [html]]
            [chord.client :refer [ws-ch]]
            [cljs.core.async :refer [chan <! >! put! close! timeout]]
            [cljs.reader :as edn]))

(enable-console-print!)

(defn store-text! [app text]
  ;; we keep the most recent 10 messages
  (->> (cons text (:texts @app))
       (take 10)
       (om/update! app :texts)))

(defn receive-text! [app server-chan]
  ;; every time we get a message from the server, add it to our list
  (go-loop []
           (when-let [text (:message (<! server-chan))]
             (println "Rx: " text)
             (store-text! app text)
             (recur))))

(defn send-text! [input-chan server-chan]
  ;; send all the messages to the server
  (go-loop []
           (when-let [text (<! input-chan)]
             (println "Tx: " text)
             (>! server-chan text)
             (recur))))

(defn bind! 
  "Do a non-blocking bind of an Om component to the specified socket address, binding the 
  web socket channel and an input channel."
  [app owner socket-addr]
  (go (let [{:keys [ws-channel error]}
            (<! (ws-ch socket-addr
                       {:format :json-kw}))
            input-channel (chan)]
        (om/set-state! owner :socket ws-channel)
        (om/set-state! owner :input input-channel)
        (send-text! input-channel ws-channel)
        (receive-text! app ws-channel))))

(def app-state (atom {:texts ["This is a default message"]}))

(defn text-view [msg]
  (html [:p msg]))

(defn texts-view [app owner]
  (reify
    om/IInitState
    (init-state [_]
      (bind! app owner "ws://localhost:8082/socket")
      {:socket nil
       :input nil})
    om/IRenderState
    (render-state [_ {:keys [socket input]}]
      (let [texts (get app :texts)]
        (html [:div
               [:div.row-fluid 
                (map text-view texts)]
               [:div.row-fluid
                [:input.input-large 
                 {:value (first texts)
                  :on-change #(when input
                                (put! input (.. % -target -value)))}]]])))))

(om/root
  texts-view
  app-state
  {:target (. js/document (getElementById "main"))})


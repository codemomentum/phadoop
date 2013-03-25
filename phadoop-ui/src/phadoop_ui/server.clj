(ns phadoop-ui.server
  (:require [ring.adapter.jetty :as jetty]
            [phadoop-ui.app :as app])
  (:gen-class))

(defn- add-port [options port]
  (let [port (if port
               (Integer/parseInt port)
               8001)]
    (assoc options :port port)))

(defn- parse-args [[port :as args]]
  (-> {}
      (add-port port)))

(defn -main [& args]
  (println "Starting server...")
  (let [options (parse-args args)
        port (:port options)
        server (jetty/run-jetty (var app/site-handler)
                                {:port port :join? false})]
    (println "Server started on port" port)
    (println (str "You can view the site at http://localhost:" port))
    server))

(defn stop [instance]
  (.stop instance)
  (println "Server stopped"))

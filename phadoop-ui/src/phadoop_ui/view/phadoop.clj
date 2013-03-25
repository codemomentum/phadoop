(ns phadoop-ui.view.phadoop
  (:require [compojure.core :refer [defroutes GET POST]]
            [stencil.core :as stencil]
            [phadoop-ui.view.common :as common]
            [phadoop-ui.util.elem :as elem]
            [clj-json.core :as json]
            ))

(defn trigger-on-cluster "Triggers the map reduce job on the hadoop cluster"
  [mappper mapper-ext reducer reducer-ext inp outp]
  (doto (org.codemomentum.phadoop.app.PHadoopJob.)
    (.startOnCluster mappper mapper-ext reducer reducer-ext inp outp)
    )
  )

(defn json-response [data & [status]]
  {:status (or status 200)
   :headers {"Content-Type" "application/json"}
   :body (json/generate-string data)})

(defroutes phadoop-routes
  (GET "/jobs" request (json-response (elem/list)))
  (POST "/job" request (let [dsl (json/parse-string (slurp (request :body )))
                             ]
                         (elem/put (common/uuid) {"dsl" dsl})
                         (print dsl)
                         (json-response ({"tracking-url"
                                          (trigger-on-cluster "function map(key, value, context){
    _key.set(key);
    _value.set(value);
    context.write(_key,_value);
}" "js"

                                            "function reduce(key, values, context){
    var iterator=values.iterator();
    while (iterator.hasNext()) {
        _key.set(key);
        _value.set(iterator.next());
        context.write(_key,_value);
    }
}" "js"
                                            "hdfs://localhost:9000/input" "hdfs://localhost:9000/output")}))
                         ))
  )

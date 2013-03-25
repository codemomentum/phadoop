(ns phadoop-ui.view.phadoop
  (:require [compojure.core :refer [defroutes GET POST]]
            [stencil.core :as stencil]
            [phadoop-ui.view.common :as common]
            [phadoop-ui.util.elem :as elem]
            [clj-json.core :as json]
            ))

(def job (org.codemomentum.phadoop.app.PHadoopJob.))

(defn trigger-on-cluster "Triggers the map reduce job on the hadoop cluster"
  [mappper mapper-ext reducer reducer-ext inp outp]
  (. job startOnCluster mappper mapper-ext reducer reducer-ext inp outp)
  )

(defn json-response [data & [status]]
  {:status (or status 200)
   :headers {"Content-Type" "application/json"}
   :body (json/generate-string data)})

(defroutes phadoop-routes
  (GET "/jobs" request (json-response (elem/list)))
  (POST "/job" {params :params} (let [uuid (common/uuid)
                                      tracking-url (trigger-on-cluster
      (params :mapper_code )
      (params :mapper_extension )
      (params :reducer_code )
      (params :reducer_extension )
      (params :input_path )
      (params :output_path )
      )]
                                  (print tracking-url)
                                  (elem/put uuid (assoc params :tracking-url tracking-url))
                                  (json-response (elem/get uuid))
                                  )))

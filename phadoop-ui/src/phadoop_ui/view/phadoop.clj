(ns phadoop-ui.view.phadoop
  (:require [compojure.core :refer [defroutes GET POST]]
            [stencil.core :as stencil]
            [phadoop-ui.view.common :as common]
            [phadoop-ui.util.elem :as elem]
            [clj-json.core :as json]
            ))

(defn json-response [data & [status]]
  {:status (or status 200)
   :headers {"Content-Type" "application/json"}
   :body (json/generate-string data)})

(defroutes phadoop-routes
  (GET "/jobs" request (json-response (elem/list)))
  (POST "/job" request (elem/put (common/uuid) {"body" (slurp (request :body))}))
)

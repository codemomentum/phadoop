(ns phadoop-ui.view.admin
  (:require [compojure.core :refer [defroutes GET]]
            [stencil.core :as stencil]
            [phadoop-ui.view.common :refer [restricted admin? wrap-layout]]))

(defn- page-body []
  (stencil/render-file
   "phadoop_ui/view/templates/admin"
   {}))

(defn- render-page [request]
  (wrap-layout "Admin"
               (page-body)))

(defroutes admin-routes
  (GET "/admin" request (restricted admin? render-page request)))

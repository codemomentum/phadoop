(ns phadoop-ui.view.profile
  (:require [compojure.core :refer [defroutes GET]]
            [stencil.core :as stencil]
            [phadoop-ui.view.common :refer [restricted authenticated? wrap-layout]]))

(defn- page-body []
  (stencil/render-file
   "phadoop_ui/view/templates/profile"
   {}))

(defn- render-page [request]
  (wrap-layout "Profile"
               (page-body)))

(defroutes profile-routes
  (GET "/profile" request (restricted authenticated? render-page request)))

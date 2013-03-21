(ns phadoop-ui.app
  (:require [clojure.core.cache :as cache]
            [compojure.core :refer [defroutes routes]]
            [compojure.handler :as handler]
            [compojure.route :as route]
            [stencil.loader :as stencil]
            [clojure.core.cache :as cache]
            [phadoop-ui.middleware.session :as session-manager]
            [phadoop-ui.middleware.context :as context-manager]))

;; Initialization
; Add required code here (database, etc.)
(stencil/set-cache (cache/ttl-cache-factory {}))
;(stencil/set-cache (cache/lru-cache-factory {}))


;; Load public routes
(require '[phadoop-ui.view.home :refer [home-routes]]
  '[phadoop-ui.view.about :refer [about-routes]])

;; Load authentication routes
(require '[phadoop-ui.view.auth :refer [auth-routes]])

;; Load private routes
(require '[phadoop-ui.view.profile :refer [profile-routes]]
  '[phadoop-ui.view.admin :refer [admin-routes]]
  '[phadoop-ui.view.phadoop :refer [phadoop-routes]])


;; Ring handler definition
(defroutes site-handler
  (-> (routes home-routes
        about-routes
        auth-routes
        profile-routes
        admin-routes
        phadoop-routes
        (route/resources "/")
        (route/not-found "<h1>Page not found.</h1>"))
    (session-manager/wrap-session)
    (context-manager/wrap-context-root)
    (handler/site)))

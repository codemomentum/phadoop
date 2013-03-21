(ns phadoop-ui.util.session
  (:require [phadoop-ui.middleware.session :as session-manager]))

(defn set-user! [user]
  (session-manager/session-put! :user user))

(defn current-user
  "Retrieve current user"
  []
  (session-manager/session-get :user))

(defn logout
  "Reset session"
  []
  (session-manager/session-clear))

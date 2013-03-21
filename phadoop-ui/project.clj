(defproject phadoop-ui "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.4.0"]
                 [clj-json "0.3.2"]
                 [ring "1.1.6"]
                 [compojure "1.1.3"]
                 [stencil "0.3.2"]
                 ]
  :plugins [[lein-ring "0.7.5"]
            [lein-idea "1.0.1"]
            ]
  :ring {:handler phadoop-ui.app/site-handler}
  :war-resources-path "resources/public"
  :main phadoop-ui.server)

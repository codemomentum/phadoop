(defproject phadoop-ui "0.1.0-SNAPSHOT"
  :description "Browser based user interface for Polyglot Hadoop."
  :url "http://example.com/FIXME"
  :license {:name "Apache License, Version 2.0"
            :url "http://www.apache.org/licenses/LICENSE-2.0"}
  :dependencies [[org.clojure/clojure "1.4.0"]
                 [clj-json "0.3.2"]
                 [ring "1.1.6"]
                 [compojure "1.1.3"]
                 [stencil "0.3.2"]
                 [org.codemomentum/phadoop-app "1.0-SNAPSHOT"]
                 [org.apache.hadoop/hadoop-client "2.0.3-alpha"]
                 [commons-httpclient/commons-httpclient "3.1"]
                 ;;reducer fails on single mode?
                 ]
  :plugins [[lein-ring "0.7.5"]
            [lein-idea "1.0.1"]
            ]
  :jvm-opts ["-Xmx768M"]
  :ring {:handler phadoop-ui.app/site-handler}
  :war-resources-path "resources/public"
  :main phadoop-ui.server)

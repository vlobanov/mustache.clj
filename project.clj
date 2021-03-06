(defproject me.vlobanov/mustache "1.5.1-SNAPSHOT"
  :min-lein-version "2.0.0"
  :description "Mustache write in java, for clojure. Fork of me.shenfeng/mustache.clj"
  :dependencies [[org.clojure/clojure "1.6.0"]]
  :warn-on-reflection true
  :java-source-paths ["src/java"]
  :jar-exclusions [#".*java$"]
  :javac-options ["-source" "1.6" "-target" "1.6" "-g" "-encoding" "utf8"]
  :plugins [[lein-swank "1.4.4"]]
  :profiles {:dev {:dependencies [[junit/junit "4.8.2"]
                                  [stencil "0.3.2"]
                                  [de.ubercode.clostache/clostache "1.3.1"]]}})

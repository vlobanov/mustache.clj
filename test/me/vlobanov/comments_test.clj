(ns me.vlobanov.comments-test
  (:use me.vlobanov.mustache
        clojure.test)
  (:import [me.vlobanov.mustache NestedKey]))

(deftest comments
  (testing "Text in <!--{ ... }--> is not rendered"
    (let [template (mk-template "Hey,<!--{ my dear }--> {{name}}")]
      (is (= "Hey, Visitor"
             (to-html template {:name "Visitor"})))))
  (testing "Text between two comments is rendered"
    (let [template (mk-template "Hey,<!--{ guard {{ just messing }--> mister<!--{ another guard }--> {{name}}")]
      (is (= "Hey, mister King"
             (to-html template {:name "King"}))))))

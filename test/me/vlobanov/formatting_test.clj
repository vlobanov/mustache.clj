(ns me.vlobanov.formatting-test
  (:use me.vlobanov.mustache
        clojure.test)
  (:import [me.vlobanov.mustache NestedKey]))

(deftest getting-value
  (testing "formatting not nested keys with 1 arg"
    (let [template (mk-template "{{greeting(name)}}")]
      (is (= "Hello, Spock!"
             (to-html template {:greeting "Hello, %s!"
                                :name "Spock"})))))
  (testing "formatting not nested keys with 2 args"
    (let [template (mk-template "{{greeting(name, lastname)}}")]
      (is (= "Hello, Mr Sirius Black!"
             (to-html template {:greeting "Hello, Mr %s %s!"
                                :name "Sirius"
                                :lastname "Black"})))))
  (testing "formatting nested keys with 2 args"
    (let [template (mk-template "{{lang.greeting(data.name, data.lastname)}}")]
      (is (= "Hello, Mr Jack Red!"
             (to-html template {:lang {:greeting "Hello, Mr %s %s!"}
                                :data {:name "Jack"
                                       :lastname "Red"}}))))))

(ns me.shenfeng.nested-key-test
  (:use me.shenfeng.mustache
        clostache.parser
        clojure.test)
  (:import [me.shenfeng.mustache NestedKey]))

(deftest getting-value
  (let [nk (NestedKey. "hello.my.friend")
          mp {:hello {:my {:friend "so glad to see you"}}}
          not-found (Object.)]
    (testing "getting existing nested value"
      (is (= "so glad to see you"
             (.getIn nk mp not-found))))
    (testing "getting non-existing on first level"
      (is (= not-found
             (.getIn nk {:bye "no!"} not-found))))
    (testing "getting non-existing on last level"
      (is (= not-found
             (.getIn nk {:hello {:my {:darling "i'm your father"}}}
                     not-found))))))

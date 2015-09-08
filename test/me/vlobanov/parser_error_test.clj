(ns me.vlobanov.parser-error-test
  (:use me.vlobanov.mustache
        clostache.parser
        clojure.test))

(deftest getting-value
  (testing "throwing error with line number"
    (is (thrown-with-msg? me.vlobanov.mustache.ParserException #"elements(.*)5"
                          (mk-template (slurp "test/not_opened.errtpl")))))
  (testing "throwing error with provided file name"
    (is (thrown-with-msg? me.vlobanov.mustache.ParserException #"test\/not_opened\.errtpl:5"
                          (mk-template (slurp "test/not_opened.errtpl")
                                       "test/not_opened.errtpl")))))

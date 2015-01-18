(ns me.vlobanov.mustache-test
  (:use me.vlobanov.mustache
        clostache.parser
        clojure.test))

(deftest test-variable
  (let [t (mk-template "{{name}}")
        name "abcdefg"]
    (is (= name (to-html t {:name name}))))
  (let [e (mk-template "{{name}}")
        name "<b>"]
    (is (= "&lt;b&gt;" (to-html e {:name name}))))
  (let [e (mk-template "{{&name}}")
        name "<b>"]
    (is (= "<b>" (to-html e {:name name}))))
  (let [e (mk-template "{{{name}}}")
        name "<b>"]
    (is (= "<b>" (to-html e {:name name})))))

(deftest test-section
  (let [t (mk-template "{{#t}}true{{/t}}")]
    (is (= "true" (to-html t {:t true}))))
  (let [t (mk-template "{{#f}}false{{/f}}")]
    (is (= "" (to-html t {:t false})))))

(deftest test-true-test
  (let [t (mk-template "{{?t}}true{{/t}}{{^t}}false{{/t}}")]
    (is (= "true" (to-html t {:t [1 2 3]})))))

(deftest test-array
  (let [t (mk-template "{{#arr}}{{.}}{{/arr}}")]
    (is (= "1234" (to-html t {:arr (range 1 5)})))))

(deftest test-comments
  (let [t (mk-template "{{!comment}}")]
    (is (= "" (to-html t {:data true})))))

(deftest test-empty-string-is-false
  (let [t (mk-template "{{#str}}abc{{/str}}")]
    (is (= "" (to-html t {:str ""})))))

(deftest test-partial
  (let [t (mk-template "Hello {{>partial}}!")]
    (is (= "Hello World"
           (to-html t {:name "World"} {:partial "{{name}}"})))))


(deftest test-nested-variables
  (let [t (mk-template "{{ hello.great.world }}")]
    (is (= "Hi!" (to-html t {:hello {:great {:world "Hi!"}}})))))

(deftest test-nested-variables-true
  (let [t (mk-template "{{?data.t}}true{{/data.t}}{{^data.t}}false{{/data.t}}")]
    (is (= "true" (to-html t {:data {:t true}})))))


(deftest test-gen-tmpls-from-resouces
  (gen-tmpls-from-resources "test" [".tpl"])
  (is (test2 {}))                       ; test2 is generate
  (is (test2))
  (is (test2)))

(deftest test-gen-tmpls-from-folder
  (gen-tmpls-from-folder "../mustache.clj" [".tpl"])
  (is (test-test2 {})) ;; => test2.clj => test-test2
  (is (test-test2))
  (is (test-test2)))

(deftemplate template (slurp "test/tpl.tpl"))
(deftemplate nested-template (slurp "test/nested_tpl.tpl"))

(def data {:title "mustache.clj - Logic-less {{mustache}} templates for Clojure"
           :list (map (fn [id]
                        {:id id
                         :name (str "name" id)}) (range 1 4))})

(def nested-data {:data {:en data}})


(dotimes [i 100000]                     ; warm up
  (template data))

(dotimes [i 100000]                     ; warm up
  (nested-template nested-data))

;; (deftemplates {:name_one "{{name}}"
;;                :name_two "Hello {{name}}, {{>name_one}}"})

;; (deftest test-deftemplates
;;   (is (= (name-one {:name "abc"}) "abc"))
;;   (is (= (name-two {:name "abc"}) "Hello abc, abc")))

(println "Perf test: Render 100k Times\n"
         (slurp "test/tpl.tpl")
         "With data\n " data
         "Take: \n"
         "Output: " (template data))
(time
 (dotimes [i 100000]
   (template data)))

(println "\nResult: \n"
         (template data))



(println "")
(println "")
(println "Nested perf test: Render 100k Times\n"
         (slurp "test/nested_tpl.tpl")
         "With data\n " nested-data
         "Take: \n"
         "Output: " (nested-template nested-data))
(time
 (dotimes [i 100000]
   (nested-template nested-data)))

(println "\nResult: \n"
         (nested-template nested-data))



(deftemplate hello-world "{{name}}")

(ns adventofcode.day5-test
  (:require [adventofcode.day5 :refer :all]
            [clojure.test :refer :all]))

(deftest passmd5-test
  (is (= (password "abc") "18f47a30")))

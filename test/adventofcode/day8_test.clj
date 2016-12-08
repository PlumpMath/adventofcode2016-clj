(ns adventofcode.day8-test
  (:require [adventofcode.day8 :refer :all]
            [clojure.test :refer :all]))

(def test-instructions [[:rect [3 2]]
                        [:rotate-col [1 1]]
                        [:rotate-row [0 4]]
                        [:rotate-col [1 1]]
                        ])

(def test-screen (vec (repeat 3 (vec (repeat 7 :off)))))

(deftest foo-test
  (is (= (rotate-row (rect blank-screen [1 1]) [0 3])
         (do-instructions blank-screen [[:rect [1 1]]
                                        [:rotate-row [0 3]]])))

  (is (= (do-instructions test-screen test-instructions)
         [[:off :on :off :off :on :off :on]
          [:on :off :on :off :off :off :off]
          [:off :on :off :off :off :off :off]]))

  (is (= (count (filter #{:on} (flatten [[:off :on :off :off :on :off :on]
                                         [:on :off :on :off :off :off :off]
                                         [:off :on :off :off :off :off :off]])))
         6)))

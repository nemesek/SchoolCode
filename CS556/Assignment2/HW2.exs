defmodule HW2 do
  def fizzbuzz(0,0,_), do: "FizzBuzz"
  def fizzbuzz(0,_,_), do: "Fizz"
  def fizzbuzz(_,0,_), do: "Buzz"
  def fizzbuzz(_,_,z), do:  z
# end

# Write a function prefix that takes a string
# it should return a new function that takes a second string.
# When the second function is called, it will return a string
# containing the first string, a space, and the second string
# mrs = prefix.("Mrs") -> mrs.("Smith") -> "Mrs Smith"
  def prefix(pre), do: (fn post -> pre <> " " <> post end)
  # def prefix(pre), do: (fn -> pre end)
end

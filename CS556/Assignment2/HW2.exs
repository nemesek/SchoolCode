defmodule HW2 do
  # Chapter 5 exercise 2
  def fizzbuzz(0,0,_), do: "FizzBuzz"
  def fizzbuzz(0,_,_), do: "Fizz"
  def fizzbuzz(_,0,_), do: "Buzz"
  def fizzbuzz(_,_,z), do:  z

  # Chapter 5 exercise 4
  # mrs = prefix.("Mrs") -> mrs.("Smith") -> "Mrs Smith"
  def prefix(pre), do: (fn post -> pre <> " " <> post end)

  # Chapter 6 exercise 4
  def sum(0), do: 0
  def sum(n) when n > 0 do n + sum(n-1) end
  def sum(n) do raise "Invalid input #{n}" end

  # Chapter 6 exercise 6
  defmodule Chop do
    def guess(actual, range) do
      low..high = range
      i = div(low + high,2)
      IO.puts("Is it #{i}")
      _makeGuess(actual,range,i)
    end

    defp _makeGuess(actual,_, guess) when actual == guess do actual end
    defp _makeGuess(actual, _..h, guess) when actual > guess do guess(actual, guess+1..h) end
    defp _makeGuess(actual, l.._, guess), do: guess(actual, l..guess - 1)
  end

  # Chapter 7 exercise 1
  def mapsum(list, func), do: _map(list,func) |> _sum()

  defp _map([], _), do: []
  defp _map([head|tail], func), do: [func.(head)| _map(tail,func)]
  defp _sum(list), do: _reduce(list, 0, &(&1 + &2))
  defp _reduce([], value, _), do: value
  defp _reduce([head|tail], value, func), do: _reduce(tail, func.(head,value), func)

  # Chapter 7 exercise 2
  def max([head|tail]), do: _max(tail,head)

  defp _max([], largest), do: largest
  defp _max([head|tail], largest) when head > largest do _max(tail,head) end
  defp _max([_|tail], largest), do: _max(tail,largest)

  # Chapter 10 exercise 5
  # all? implementation I only intended to do this one so I guess this
  # is the one that should be graded
  def all?([], _), do: true
  def all?([head|tail], func) do
    if func.(head) do
      all?(tail, func)
    else
      false
    end
  end

  # each implementation, did it for fun
  def each([], _), do: :ok
  def each([head|tail], func) do
    func.(head)
    each(tail,func)
  end

  # split implementation, did it for fun
  # just reused your implementation of take and reverse
  def split(list, n) do
     {
       _take(list,n),
       _reverse(_take(_reverse(list,[]), length(list) - n),[])
     }
   end

  defp _take([], _)            do [] end
  defp _take(_, n) when n <= 0 do [] end
  defp _take([x|xs], n)        do [ x | _take(xs,n-1) ] end
  defp _reverse([],ys)      do ys end
  defp _reverse([x|xs], ys) do _reverse(xs, [x|ys]) end

  defmodule Test do

    def runTests() do
      # fizzbuzz tests
      _runTest(fn -> HW2.fizzbuzz(0,0,9) == "FizzBuzz" end, "Calling fizzbuzz(0,0,9)")
      _runTest(fn -> HW2.fizzbuzz(0,9,9) == "Fizz" end, "Calling fizzbuzz(0,9,9)")
      _runTest(fn -> HW2.fizzbuzz(1,0,2) == "Buzz" end, "Calling fizzbuzz(1,0,2)")
      _runTest(fn -> HW2.fizzbuzz(7,4,1) == 1 end, "Calling fizzbuzz (7,4,1)")

      # prefix tests
      _runTest(fn -> HW2.prefix("mrs").("smith") == "mrs smith" end, "Calling prefix with mrs smith")
      _runTest(fn -> HW2.prefix("").("smith") == " smith" end, "Calling prefix with empty smith")
      _runTest(fn -> HW2.prefix("").("") == " " end, "Calling prefix with empty empty")
      _runTest(fn -> HW2.prefix("mrs").("") == "mrs " end, "Calling prefix with mrs empty")

      # sum tests
      _runTest(fn -> HW2.sum(5) == 15 end, "Calling sum(5)")
      _runTest(fn -> HW2.sum(0) == 0 end, "Calling sum(0)")

      # Chop.guess tests
      _runTest(fn -> HW2.Chop.guess(273, 1..1000) == 273 end, "Calling Chop.guess(273,1..1000)")
      _runTest(fn -> HW2.Chop.guess(273, 1..5000) == 273 end, "Calling Chop.guess(273,1..5000)")

      # mapsum tests
      _runTest(fn -> HW2.mapsum([1,2,3], &(&1 * &1)) == 14 end, "Calling mapsum([1,2,3], &(&1 * &1))")
      _runTest(fn -> HW2.mapsum([1,2,3], &(&1 + 10)) == 36 end, "Calling mapsum([1,2,3], &(&1 + 10))")
      _runTest(fn -> HW2.mapsum([], fn -> 100 end) == 0 end, "Calling mapsum([])")

      # max tests
      _runTest(fn -> HW2.max([1,2,3]) == 3 end, "Calling max [1,2,3]")
      _runTest(fn -> HW2.max([3,2,1]) == 3 end, "Calling max [3,2,1]")
      _runTest(fn -> HW2.max([1,3,2]) == 3 end, "Calling max [1,3,2]")
      _runTest(fn -> HW2.max([-1,-2,-4,-3]) == -1 end, "Calling max with all negatives")

      # all? tests
      _runTest(fn -> HW2.all?([1,2,3], &(&1 > 0)) end, "Calling all?[1,2,3] greater than zero -> T")
      _runTest(fn -> !(HW2.all?([1,2,3], &(&1 > 100))) end, "Calling all?[1,2,3] greater than 100 -> F")
      _runTest(fn -> !(HW2.all?([1,2,3], &(&1 > 1))) end, "Calling all? [1,2,3] greater than 1 -> F")
    end
    
    defp _runTest(condition, message) do
      IO.puts message
      if condition.() do IO.puts "Passed" else IO.puts "Failed" end
    end
  end

end

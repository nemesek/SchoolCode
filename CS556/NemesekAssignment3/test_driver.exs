defmodule Tester do
  import :timer
  def runStarTest do
    _runStarTest(5,8,"Testing with 5 processes and 8 messages")
    _runStarTest(3,4,"Testing with 3 processes and 4 messages")
    _runStarTest(0,5,"Testing with 0 processes and 5 messages")
    _runStarTest(5,0,"Testing with 5 processes and 0 messages")
    _runStarTest(-3,4,"Testing with negative processes and positive messages")
    _runStarTest(4,-3,"Testing with positive processes and negative messages")
  end

  defp _runStarTest(n,m,msg) do
    IO.puts msg
    Star.run(n,m)
    sleep 500
    IO.puts "**********Test Complete******************************"
  end

  def runMSTest do
    _startMasterSlave
  end

  defp _startMasterSlave do
    IO.puts "Starting Master Slave with 10 Slaves"
    MS.start(10)
    list = Enum.to_list 1..10
    Enum.map(list, fn(x) -> _runMSTest(x, "Foo") end)
    _runMSTest(5, :die)
    _runMSTest(1, "die")
    _runMSTest(5, "Hello again number 5")
    _runMSTest(1, "Hello again number 1")
    _runMSTest(0, "Below range of valid N values")
    _runMSTest(-1, "Negative N value")
    _runMSTest(11, "Above range of a valid N value")
    IO.puts "**********Sending die message to everyone.************"
    Enum.map(list, fn(x) -> _runMSTest(x, :die) end)
    sleep 500
    IO.puts "**********Test Complete******************************"
  end
  defp _runMSTest(n, msg) do
    IO.puts "Sending to slave #{n} msg: #{msg}"
    MS.to_slave(msg, n)
  end

end

defmodule Star do
  def processMessage() do
    receive do
      {:shutdown} ->
        IO.puts "++++++ #{inspect self} about to shutdown gracefully"
        exit(:normal)
      {msg} ->
        IO.puts "#{inspect self} Processed message #{msg}"
        processMessage
    end
  end

  def run(n,m) do _run(n,m,[]) end
  defp _run(0,m,pids) do
     IO.puts "DONE SPAWNING"
     pids
     |> Enum.map(fn(p) -> sendMessages(m,p) end)
     IO.puts "DONE SENDING MESSAGES"
  end

  defp _run(n,m,pids) do
    #Process.flag(:trap_exit,true)
    if n > 0 do
      pid = spawn_link(Star,:processMessage,[])
      _run(n-1,m,[pid|pids])
    end
  end

  def sendMessages(m, pid) do _sendMessages(m, pid) end
  defp _sendMessages(0, pid) do
    send pid, {:shutdown}
  end
  defp _sendMessages(m, pid) do
    if m > 0 do
      send pid,{"Message Id: #{m}"}
      _sendMessages(m-1,pid)
    end
  end
end

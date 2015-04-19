defmodule Star do
  def process() do
    receive do
      {:shutdown} -> exit(:boom)
       {msg} ->
        IO.puts "Processed message #{msg}"
        process
    end
  end

  def run(n,m) do _run(n,m,[]) end
  defp _run(0,m,pids) do
     IO.puts "DONE SPAWNING"
     pids
     |> Enum.map(fn(p) -> sendMessages(m,p,self()) end)
     IO.puts "DONE SENDING MESSAGES"
  end

  defp _run(n,m,pids) do
    Process.flag(:trap_exit,true)
    pid = spawn_link(Star,:process,[])
    _run(n-1,m,[pid|pids])
  end

  def handleCallback(pid) do
    receive do
      {^pid,{:ok,message}} -> IO.puts "Called back #{message}"
    end
  end

  def sendMessages(m, pid, sender) do _sendMessages(m, pid,sender) end
  defp _sendMessages(0, pid,_) do
    send pid, {:shutdown}
  end
  defp _sendMessages(m, pid,sender) do
    send pid,{"Message Id: #{m}"}
    #handleCallback(pid)
    _sendMessages(m-1,pid,sender)
  end
end

Star.run(4,2)

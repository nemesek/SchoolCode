defmodule Star do
  def process() do
    IO.puts "Got Spawned"
    receive do
       {msg} ->
        IO.puts "Got a message #{msg}"
        #send sender,{me,{:ok,"#{msg}"}}
      {:shutdown} -> exit(:boom)
    end
  end

  def run(n,m) do _run(n,m,[]) end
  defp _run(0,m,pids) do
     IO.puts "DONE SPAWNING"
     pids
     |> Enum.map(fn(p) -> sendMessages(m,p,self()) end)
     IO.puts "Done"
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

  def sendMessages(m, pid, sender) do _sendMessages(m, pid, "Blah",sender) end
  defp _sendMessages(0, pid, _,_) do
    send pid, {:shutdown}
  end
  defp _sendMessages(m, pid, msg,sender) do
    send pid,{"Blah"}
    #handleCallback(pid)
    _sendMessages(m-1,pid,msg,sender)
  end
end

Star.run(4,1)

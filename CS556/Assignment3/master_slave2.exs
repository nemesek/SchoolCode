defmodule MS do
  @name :master
  def start(n) do
    pid = spawn(MS,:_start,[n])
    # per instructions registering as the registered process master
    :global.register_name(@name,pid)
  end
  def _start(n) do
     pids = _run(n,[])
     _setupReceiveHandlers(pids)
  end

  def to_slave(msg,n) do
    send :global.whereis_name(@name), {n,msg}
  end

  defp _setupReceiveHandlers(pids) do
    receive do
      {:EXIT,sender,msg} ->
        index = Enum.find_index(pids,fn(p) -> p == sender end)
        newPid = pid = spawn_link(MS,:slave,[msg])
        newPids = List.replace_at(pids,index,newPid)
        IO.puts "Initialized new process for N = #{msg}"
        _setupReceiveHandlers(newPids)
      {p, msg} ->
        pid = Enum.at(pids,p-1)
        if pid == nil do
          IO.puts "#{p} is not a valid value for the range of processes."
          _setupReceiveHandlers(pids)
        else
          send pid,{msg}
          _setupReceiveHandlers(pids)
        end
      end
  end

  defp _run(0,pids) do pids end
  defp _run(n, pids) do
    Process.flag(:trap_exit,true)
    pid = spawn_link(MS,:slave,[n])
    _run(n-1,[pid|pids])
  end

  def slave(p) do
    receive do
      {"die"} -> exit(p)
      {:die} -> exit(p)
      {msg} -> IO.puts "Slave #{p} Processed: #{msg}"
      slave(p)
    end
  end
end

# pid = spawn(MS,:start,[5])

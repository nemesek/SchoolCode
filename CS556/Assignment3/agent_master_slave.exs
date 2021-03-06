defmodule MS do
  @name :master
  def start(n) do
    func = _start
    Agent.start_link(fn n -> end, name: @name)
  end

  def to_slave(msg,n, x) do
    send @name, {n,msg}
  end

  def _start(n) do
     pids = _run(n,[])
     _setupCallback(pids)
  end

  defp _setupCallback(pids) do
    receive do
      {:EXIT,sender,msg} ->
        index = Enum.find_index(pids,fn(p) -> p == sender end)
        newPid = pid = spawn_link(MS,:slave,[])
        newPids = List.replace_at(pids,index,newPid)
        IO.puts "Initialized new process for N = #{msg}"
        _setupCallback(newPids)
      {p, msg} ->
        pid = Enum.at(pids,p-1)
        send pid,{self,{msg, p}}
        _setupCallback(pids)
      end
  end

  defp _run(0,pids) do pids end
  defp _run(n, pids) do
    Process.flag(:trap_exit,true)
    pid = spawn_link(MS,:slave,[])
    _run(n-1,[pid|pids])
  end

  def slave do
    receive do
      {sender,{"die",p}} ->
        exit(p)
      {_,{msg,_}} -> IO.puts "Slave Processed: #{msg}"
      slave
    end
  end
end

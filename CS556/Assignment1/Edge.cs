namespace Assignment1
{
    public class Edge<T1, T2,T3 >
    {
        private readonly T1 _label;
        private readonly Vertex<T2, T3> _source;
        private readonly Vertex<T2, T3> _destination;

        public Edge(T1 label, Vertex<T2, T3> source, Vertex<T2, T3> destination)
        {
            _label = label;
            _source = source;
            _destination = destination;
        }

        public T1 Label { get { return _label; } }
        public Vertex<T2, T3> Source { get { return _source; } }
        public Vertex<T2, T3> Destination { get { return _destination; } }
        
    }
}

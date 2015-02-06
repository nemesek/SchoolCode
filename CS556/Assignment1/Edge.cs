using System;

namespace Assignment1
{
    public class Edge<T1, T2,T3 >
    {
        private readonly T1 _label;
        private readonly Tuple<Vertex<T2, T3>, Vertex<T2, T3>> _arc;

        public Edge(T1 label, Vertex<T2, T3> source, Vertex<T2, T3> destination)
        {
            _label = label;
            _arc = new Tuple<Vertex<T2, T3>, Vertex<T2, T3>>(source, destination);
        }

        public T1 Label { get { return _label; } }
        public Vertex<T2, T3> DirectPredecessor { get { return _arc.Item1; } }
        public Vertex<T2, T3> DirectSuccessor { get { return _arc.Item2; } }
        
    }
}

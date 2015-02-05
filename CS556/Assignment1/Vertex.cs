namespace Assignment1
{
    public class Vertex<T1, T2>
    {
        private readonly T1 _identifier;
        private readonly T2 _label;

        public Vertex(T1 identifier)
        {
            _identifier = identifier;
            _label = default(T2);
        }

        public Vertex(T1 identifier, T2 label)
        {
            _identifier = identifier;
            _label = label;
        }

        public T1 Identifier { get { return _identifier; } }
        public T2 Label { get { return _label; } }
    }
}

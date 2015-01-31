using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Assignment1
{
    public class Vertex<T>
    {
        T _data;
        public T Label  
        {
            get { return _data; }
            set { _data = value; }
        }
    }
}

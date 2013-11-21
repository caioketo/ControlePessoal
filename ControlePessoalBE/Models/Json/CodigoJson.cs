using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;

namespace ControlePessoalBE.Models.Json
{
    public class CodigoJson
    {
        public int CodigoId { get; set; }
        public DateTime Cadastro { get; set; }
        public string Codigo { get; set; }

        public CodigoJson(CodigoModel codigo)
        {
            this.CodigoId = codigo.CodigoId;
            this.Cadastro = codigo.Cadastro;
            this.Codigo = codigo.Codigo;
        }
    }
}
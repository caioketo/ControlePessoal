using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;

namespace ControlePessoalBE.Models.Json
{
    public class PrecoJson
    {
        public int PrecoId { get; set; }
        public DateTime Cadastro { get; set; }
        public double Preco { get; set; }

        public PrecoJson(PrecoModel preco)
        {
            this.PrecoId = preco.PrecoId;
            this.Cadastro = preco.Cadastro;
            this.Preco = preco.Preco;
        }
    }
}
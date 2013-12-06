using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;

namespace ControlePessoalBE.Models.Json
{
    public class PassoDeReceitaJson
    {
        public int PassoDeReceitaId { get; set; }
        public int Ordem { get; set; }
        public string Descricao { get; set; }

        public PassoDeReceitaJson(PassoDeReceitaModel passo)
        {
            if (passo != null)
            {
                this.PassoDeReceitaId = passo.PassoDeReceitaId;
                this.Ordem = passo.Ordem;
                this.Descricao = passo.Descricao;
            }
        }
    }
}
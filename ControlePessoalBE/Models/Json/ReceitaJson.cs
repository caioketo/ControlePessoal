using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;

namespace ControlePessoalBE.Models.Json
{
    public class ReceitaJson
    {
        public int ReceitaId { get; set; }
        public virtual List<ItemDeReceitaJson> Igredientes { get; set; }
        public virtual List<PassoDeReceitaJson> Passos { get; set; }
        public string Descricao { get; set; }

        public ReceitaJson(ReceitaModel receita)
        {            
            Igredientes = new List<ItemDeReceitaJson>();
            Passos = new List<PassoDeReceitaJson>();

            if (receita != null)
            {
                ReceitaId = receita.ReceitaId;
                Descricao = receita.Descricao;
            }
        }
    }
}
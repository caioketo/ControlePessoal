using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;

namespace ControlePessoalBE.Models.Json
{
    public class LocalJson
    {
        public int LocalId { get; set; }
        public string Descricao { get; set; }

        public LocalJson(LocalModel model)
        {
            this.LocalId = model.LocalId;
            this.Descricao = model.Descricao;
        }
    }
}
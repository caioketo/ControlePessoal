using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;
using System.Linq;
using System.Web;

namespace ControlePessoalBE.Models
{
    public class TesteModel
    {
        [Key]
        public int TesteId { get; set; }
        public string Descricao { get; set; }
    }
}
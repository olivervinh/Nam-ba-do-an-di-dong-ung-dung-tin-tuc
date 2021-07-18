using API_News.Data;
using API_News.Models;
using Microsoft.AspNetCore.Mvc;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;

// For more information on enabling Web API for empty projects, visit https://go.microsoft.com/fwlink/?LinkID=397860

namespace API_News.Controllers
{
    [Route("api/[controller]")]
    [ApiController]
    public class ReportsController : ControllerBase
    {
        private readonly DPContext _context;
        public ReportsController(DPContext context)
        {
            this._context = context;
        }
        // GET: api/<ReportsController>
        [HttpGet]
        public IEnumerable<string> Get()
        {
            return new string[] { "value1", "value2" };
        }

        // GET api/<ReportsController>/5
        [HttpGet("{id}")]
        public string Get(int id)
        {
            return "value";
        }

        // POST api/<ReportsController>
        [HttpPost]
        public async Task<ActionResult<Report>> PostAsync([FromForm] string idArticle, [FromForm] string idUser, [FromForm] string content)
        {
            int iduser = int.Parse(idUser);
            int idarticles = int.Parse(idArticle);
            Report report = new Report()
            {
                IdArticle = idarticles,
                IdUser = iduser,
                Content = content
            };
            _context.Reports.Add(report);
            await _context.SaveChangesAsync();
            return Ok();
        }

        // PUT api/<ReportsController>/5
        [HttpPut("{id}")]
        public void Put(int id, [FromBody] string value)
        {
        }

        // DELETE api/<ReportsController>/5
        [HttpDelete("{id}")]
        public void Delete(int id)
        {
        }
    }
}

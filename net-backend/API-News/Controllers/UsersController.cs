using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using Microsoft.AspNetCore.Http;
using Microsoft.AspNetCore.Mvc;
using Microsoft.EntityFrameworkCore;
using API_News.Data;
using API_News.Models;
using Newtonsoft.Json;

namespace API_News.Controllers
{
    [Route("api/[controller]")]
    [ApiController]
    public class UsersController : ControllerBase
    {
        private readonly DPContext _context;

        public UsersController(DPContext context)
        {
            _context = context;
        }

        // GET: api/Users
        [HttpGet]
        public async Task<ActionResult<IEnumerable<User>>> GetUsers()
        {
            return await _context.Users.ToListAsync();
        }

        // GET: api/Users/5
        [HttpGet("{id}")]
        public async Task<ActionResult<User>> GetUser(int id)
        {
            var user = await _context.Users.FindAsync(id);

            if (user == null)
            {
                return NotFound();
            }

            return user;
        }

       
        // PUT: api/Users/5
        // To protect from overposting attacks, see https://go.microsoft.com/fwlink/?linkid=2123754
        [HttpPut("{id}")]
        public async Task<IActionResult> PutUser(int id, [FromForm] string username, [FromForm] string password, [FromForm] string fullname)
        {
            User user = await _context.Users.FindAsync(id);
            user.Password = password;
            user.Username = username;
            user.Fullname = fullname;
            if (id != user.Id)
            {
                return BadRequest();
            }

            _context.Users.Update(user);

            try
            {
                await _context.SaveChangesAsync();
            }
            catch (DbUpdateConcurrencyException)
            {
                if (!UserExists(id))
                {
                    return NotFound();
                }
                else
                {
                    throw;
                }
            }

            return Ok();
        }
        [HttpPost("{dangnhap}")]
        public async Task<ActionResult<User>> UserDangNhap([FromForm]string username, [FromForm] string password)
        {
            User us = await _context.Users.FirstOrDefaultAsync(s => s.Username == username && s.Password == password);
            if (us == null)
            {
                return null;
            }
            var response = new
            {
                id = us.Id,
                fullname =us.Fullname,
                username = us.Username,
                password = us.Password,
            };

            var json = JsonConvert.SerializeObject(response);
            return new OkObjectResult(json);
          
        }
        // POST: api/Users
        // To protect from overposting attacks, see https://go.microsoft.com/fwlink/?linkid=2123754
        [HttpPost]
        public async Task<ActionResult<User>> PostUser([FromForm] string username, [FromForm] string password, [FromForm] string fullname )
        {
            User user = new User()
            {
                Fullname = fullname,
                Username = username,
                Password = password,
            };
            _context.Users.Add(user);
            await _context.SaveChangesAsync();

            return Ok();
        }

        // DELETE: api/Users/5
        [HttpDelete("{id}")]
        public async Task<IActionResult> DeleteUser(int id)
        {
            var user = await _context.Users.FindAsync(id);
            if (user == null)
            {
                return NotFound();
            }

            _context.Users.Remove(user);
            await _context.SaveChangesAsync();

            return NoContent();
        }

        private bool UserExists(int id)
        {
            return _context.Users.Any(e => e.Id == id);
        }
    }
}

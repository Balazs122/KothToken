![image](https://media.discordapp.net/attachments/1206908041912385536/1342540586120515735/PyrexTokenRebrand.png?ex=67ba01da&is=67b8b05a&hm=e95dce6a68c764df8736a20021d9b2fbefee77a2576dc3ffe705587bafedbf4b&=&format=webp&quality=lossless&width=1920&height=394)

# PyrexToken 🔥  
A **fully open-source** and **free** Minecraft plugin that introduces a **token-based currency system**, perfect for in-game shops, rewards, and events.  

## 📌 Features  
✅ **Custom Placeholder** – `%pyrextoken_token_value%` *(Requires PlaceholderAPI)*  
✅ **Player Token Management** – `/pyrextoken add/set/remove [player] [amount]`  
✅ **Balance Command** – `/pyrextoken balance` *(Players can check their own balance)*  
✅ **Storage Options** – Choose between **file storage** and **MySQL** *(Coming Soon!)*  
✅ **Admin Commands & Permissions** – Only authorized players can modify token balances  
✅ **Instant Plugin Reload** – `/pyrextoken reload` to apply config changes without restarting  
✅ **Future Multi-Language Support** *(Coming Soon!)*  
✅ **Shop Integration** – Works with external shop plugins using tokens as currency  

---

## 📥 Installation  
1. **Download the latest release** from the [Releases](https://github.com/Balazs122/PyrexToken/releases) page.  
2. **Drop the `.jar` file** into your `plugins` folder.  
3. **Start your server** to generate the `config.yml` file.  
4. **Configure your storage mode** (File or MySQL) in `config.yml`.  
5. **Install PlaceholderAPI** *(Required for placeholders to work)*.  

---

## 🔧 Commands & Permissions  
| Command | Description | Permission |
|---------|------------|------------|
| `/pyrextoken balance` | Check your token balance | *(Everyone)* |
| `/pyrextoken add [player] [amount]` | Add tokens to a player | `pyrextoken.admin.manage` |
| `/pyrextoken remove [player] [amount]` | Remove tokens from a player | `pyrextoken.admin.manage` |
| `/pyrextoken set [player] [amount]` | Set a player’s token balance | `pyrextoken.admin.manage` |
| `/pyrextoken reload` | Reload the config file | `pyrextoken.admin.reload` |
| `/pyrextoken version` | Show plugin version | *(Everyone)* |
| `/pyrextoken help` | Show help menu | *(Everyone)* |

---

## 📢 PlaceholderAPI Integration  
**Required for placeholders to work!**  
- Use `%pyrextoken_token_value%` to display a player’s token balance.  
- Example: Add it to the scoreboard, tablist, or chat formatting plugin.  

---

## 🛠️ Changelog  
Check out the full **[changelog here](https://github.com/Balazs122/PyrexToken/blob/master/CHANGELOG.md)** for recent updates, bug fixes, and upcoming features!  

---

## 📜 License  
This project is **open-source** and released under the [MIT License](LICENSE).  

🚀 Enjoy the plugin? **Star the repo!** ⭐  


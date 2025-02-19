# KothToken 🔥  
A **fully open-source** and **free** Minecraft plugin that introduces a **token-based currency system**, perfect for in-game shops, rewards, and events.  

## 📌 Features  
✅ **Custom Placeholder** – `%kothtoken_koth_token_value%` *(Requires PlaceholderAPI)*  
✅ **Player Token Management** – `/kothtoken add/set/remove [player] [amount]`  
✅ **Balance Command** – `/kothtoken balance` *(Players can check their own balance)*  
✅ **Storage Options** – Choose between **file storage** and **MySQL** *(Coming Soon!)*  
✅ **Admin Commands & Permissions** – Only authorized players can modify token balances  
✅ **Instant Plugin Reload** – `/kothtoken reload` to apply config changes without restarting  
✅ **Future Multi-Language Support** *(Coming Soon!)*  
✅ **Shop Integration** – Works with external shop plugins using tokens as currency  

---

## 📥 Installation  
1. **Download the latest release** from the [Releases](https://github.com/Balazs122/KothToken/releases) page.  
2. **Drop the `.jar` file** into your `plugins` folder.  
3. **Start your server** to generate the `config.yml` file.  
4. **Configure your storage mode** (File or MySQL) in `config.yml`.  
5. **Install PlaceholderAPI** *(Required for placeholders to work)*.  

---

## 🔧 Commands & Permissions  
| Command | Description | Permission |
|---------|------------|------------|
| `/kothtoken balance` | Check your token balance | *(Everyone)* |
| `/kothtoken add [player] [amount]` | Add tokens to a player | `kothtoken.admin.manage` |
| `/kothtoken remove [player] [amount]` | Remove tokens from a player | `kothtoken.admin.manage` |
| `/kothtoken set [player] [amount]` | Set a player’s token balance | `kothtoken.admin.manage` |
| `/kothtoken reload` | Reload the config file | `kothtoken.admin.reload` |
| `/kothtoken version` | Show plugin version | *(Everyone)* |
| `/kothtoken help` | Show help menu | *(Everyone)* |

---

## 📢 PlaceholderAPI Integration  
**Required for placeholders to work!**  
- Use `%kothtoken_koth_token_value%` to display a player’s token balance.  
- Example: Add it to the scoreboard, tablist, or chat formatting plugin.  

---

## 🛠️ Changelog  
Check out the full **[changelog here](https://github.com/Balazs122/KothToken/blob/master/CHANGELOG.md)** for recent updates, bug fixes, and upcoming features!  

---

## 📜 License  
This project is **open-source** and released under the [MIT License](LICENSE).  

🚀 Enjoy the plugin? **Star the repo!** ⭐  

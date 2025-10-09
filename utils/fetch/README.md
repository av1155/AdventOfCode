# fetch_aoc_readme.sh

Fetches **Advent of Code** problem descriptions and saves them as `README.md` files inside each local `dayXX` folder.  
It supports **any year**, handles both **padded (`day01`)** and **unpadded (`day1`)** directories, and creates padded ones if missing.

---

## 🧩 Features

- Works for any given year (`2023`, `2024`, `2025`, etc.)
- Handles both directory styles: `day1` and `day01`
- Automatically creates missing day folders
- Converts HTML problem pages into Markdown using:
    - `pandoc` (preferred, produces rich Markdown)
    - or `w3m` / `lynx` as fallback
- Waits 1 second between requests to respect AoC servers

---

## ⚙️ Requirements

- macOS or Linux with `zsh`
- `curl`
- One of:
    - `pandoc` (recommended)
    - or `w3m`
    - or `lynx`

> If using `pandoc`, the script references an optional Lua filter at:
> `~/scripts/scripts/html-to-text/abs.lua`  
> You can safely remove or change that path if not used.

---

## 🚀 Usage

```bash
# General syntax
/bin/zsh utils/fetch/fetch_aoc_readme.sh [YEAR] [ROOT_PATH]

# Defaults
# YEAR = 2024
# ROOT_PATH = current working directory ($PWD)
```

### Examples

```bash
# Fetch AoC 2023 READMEs into your AdventOfCode repo
/bin/zsh utils/fetch/fetch_aoc_readme.sh 2023 /Users/andreaventi/Developer/AdventOfCode

# Fetch AoC 2024 READMEs from current directory (defaults)
/bin/zsh utils/fetch/fetch_aoc_readme.sh
```

---

## 📁 Behavior

For each day (1–25):

1. Detects existing `day01` or `day1` directory.
2. Creates `day01` if neither exists.
3. Downloads from:  
   `https://adventofcode.com/<YEAR>/day/<N>`
4. Writes converted content to `README.md` in that directory.

---

## 📝 Notes

- The script sets a simple `User-Agent` to avoid HTTP 403 errors.
- To avoid rate-limiting, the script pauses 1 second between fetches.
- Be courteous to [Advent of Code’s terms](https://adventofcode.com/), and don’t spam requests.

---

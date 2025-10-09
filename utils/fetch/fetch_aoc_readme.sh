#!/bin/zsh

YEAR="${1:-2024}"
ROOT="${2:-$PWD}"
BASE_DIR="${ROOT%/}/${YEAR}"

# ANSI color codes
RED='\033[0;31m'
GREEN='\033[0;32m'
YELLOW='\033[1;33m'
BLUE='\033[0;34m'
NC='\033[0m' # No Color

# --- Helpers ---

percent_decode() {
    local s="$1"
    s="${s//\\/\\\\}"
    s="${s//%/\\x}"
    printf '%b' "$s"
}

sanitize_filename() {
    local s="$1"
    s="${s//[[:space:]]/_}"
    s="$(printf '%s' "$s" | tr -cd '[:alnum:]._-\n')"
    [[ -z "$s" ]] && s="output-$(date +%Y%m%d-%H%M%S)"
    printf '%s' "$s"
}

basename_from_url() {
    local url="$1" base last
    url="${url%%\?*}"; url="${url%%\#*}"
    base="${url%/}"; last="$(basename "$base")"
    if [[ -z "$last" || "$last" == "/" ]]; then
        printf 'output-%s' "$(date +%Y%m%d-%H%M%S)"; return
    fi
    last="$(percent_decode "$last")"
    while [[ "$last" == *.* ]]; do last="${last%.*}"; done
    last="$(sanitize_filename "$last")"
    [[ -z "$last" ]] && last="output-$(date +%Y%m%d-%H%M%S)"
    printf '%s' "$last"
}

choose_converter() {
    if command -v pandoc &>/dev/null; then
        echo "pandoc md"
    elif command -v w3m &>/dev/null; then
        echo "w3m txt"
    elif command -v lynx &>/dev/null; then
        echo "lynx txt"
    else
        echo "none txt"
    fi
}

base_from_url() {
    local u="$1"
    u="${u%%\#*}"; u="${u%%\?*}"
    [[ "$u" != */ ]] && u="${u%/*}/"
    printf '%s' "$u"
}

detect_base_href() {
    awk 'BEGIN{ IGNORECASE=1 }
         match($0, /<base[^>]*href=["\047]([^"\047]+)["\047]/, m) { print m[1]; exit }'
}

# --- Main ---

fetch_day() {
    local day_num="$1"
    local output_dir="$2"
    local website_url="https://adventofcode.com/${YEAR}/day/${day_num}"

    read converter def_ext <<<"$(choose_converter)"
    if [[ "$converter" == "none" ]]; then
        echo -e "${RED}No converter found. Install one of: pandoc (recommended), w3m, or lynx.${NC}"
        return 1
    fi

    mkdir -p "$output_dir"
    local output_file="${output_dir}/README.md"

    echo -e "${YELLOW}Fetching Day ${day_num}: '$website_url' → '$output_file'...${NC}"

    case "$converter" in
    pandoc)
        local filter_path
        filter_path="$HOME/scripts/scripts/html-to-text/abs.lua"

        local html
        # User-Agent helps avoid occasional 403s
        html="$(curl -fsSL -H 'User-Agent: aoc-readme-fetcher' "$website_url")" || {
            echo -e "${RED}fetch failed for day ${day_num}.${NC}"
            return 1
        }

        local detected_base BASE_URL
        detected_base="$(printf '%s' "$html" | detect_base_href 2>/dev/null)"
        if [[ -n "$detected_base" ]]; then
            BASE_URL="$(base_from_url "$detected_base")"
        else
            BASE_URL="$(base_from_url "$website_url")"
        fi

        if ! printf '%s' "$html" | BASE_URL="$BASE_URL" pandoc -f html -t gfm --reference-links --wrap=none \
            --lua-filter="$filter_path" -o "$output_file"; then
            echo -e "${RED}pandoc conversion failed for day ${day_num}.${NC}"
            return 1
        fi
        ;;
    w3m)
        if ! w3m -dump -cols 10000 "$website_url" >"$output_file"; then
            echo -e "${RED}w3m conversion failed for day ${day_num}.${NC}"
            return 1
        fi
        ;;
    lynx)
        if ! lynx -dump -width=10000 "$website_url" | sed -E 's/^[[:space:]]{2,}//' >"$output_file"; then
            echo -e "${RED}lynx conversion failed for day ${day_num}.${NC}"
            return 1
        fi
        ;;
    esac

    echo -e "${GREEN}Day ${day_num} saved to '$output_file' successfully.${NC}"
}

# Loop days; support padded and unpadded dirs; create padded if neither exists
for day in {1..25}; do
    day_padded=$(printf "%02d" $day)
    cand_padded="${BASE_DIR}/day${day_padded}"
    cand_unpadded="${BASE_DIR}/day${day}"

    if   [[ -d "$cand_padded" ]]; then day_dir="$cand_padded"
    elif [[ -d "$cand_unpadded" ]]; then day_dir="$cand_unpadded"
    else
        day_dir="$cand_padded"
        mkdir -p "$day_dir"
    fi

    fetch_day "$day" "$day_dir"
    sleep 1
done

echo -e "${GREEN}All days fetched!${NC}"

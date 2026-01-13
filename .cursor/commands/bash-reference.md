# Bash Quick Reference

Common Bash patterns with safety flags explained. Focus on practical scripts.

## Script Header (Always Include)
```bash
#!/bin/bash
set -euo pipefail  # Exit on error, undefined vars, pipe failures
# set -x  # Uncomment for debugging (prints each command)

# Explanation:
# -e: Exit immediately if any command exits with non-zero status
# -u: Treat unset variables as errors (prevents typos)
# -o pipefail: Pipeline fails if any command fails (not just the last one)
```

**Why?** Fail fast and predictably. Prevents cascading errors.

## Variables
```bash
# Variable assignment (no spaces around =)
name="Atin"
count=42

# Use quotes to preserve spaces
message="Hello World"

# Command substitution
current_date=$(date +%Y-%m-%d)
file_count=$(ls | wc -l)

# Parameter expansion
echo "${name}"           # Always use braces for clarity
echo "${name:-default}"  # Use default if unset
echo "${name:?error}"    # Error if unset

# Arrays
fruits=("apple" "banana" "cherry")
echo "${fruits[0]}"      # apple
echo "${fruits[@]}"      # all elements
echo "${#fruits[@]}"     # array length
```

## Conditionals
```bash
# File tests
if [[ -f "file.txt" ]]; then
    echo "File exists"
fi

if [[ -d "directory" ]]; then
    echo "Directory exists"
fi

# Common file tests:
# -f: regular file exists
# -d: directory exists
# -r: readable
# -w: writable
# -x: executable
# -s: file not empty

# String comparison
if [[ "$name" == "Atin" ]]; then
    echo "Match"
fi

if [[ -z "$name" ]]; then     # Empty string
    echo "Name is empty"
fi

if [[ -n "$name" ]]; then     # Non-empty string
    echo "Name is set"
fi

# Number comparison
if [[ $count -gt 10 ]]; then  # Greater than
    echo "Greater"
fi

# Operators: -eq, -ne, -lt, -le, -gt, -ge

# Logical operators
if [[ $count -gt 10 && $count -lt 20 ]]; then
    echo "Between 10 and 20"
fi
```

## Loops
```bash
# For loop (iterate list)
for file in *.txt; do
    echo "Processing $file"
done

# For loop (C-style)
for ((i=0; i<10; i++)); do
    echo "$i"
done

# For loop (array)
for fruit in "${fruits[@]}"; do
    echo "$fruit"
done

# While loop
while read -r line; do
    echo "$line"
done < input.txt

# While with condition
count=0
while [[ $count -lt 5 ]]; do
    echo "$count"
    ((count++))
done
```

## Functions
```bash
# Function definition
function greet() {
    local name="$1"  # Always use local for variables
    echo "Hello, $name"
}

# Call function
greet "Atin"

# Return status (0 = success, 1-255 = error)
function check_file() {
    if [[ -f "$1" ]]; then
        return 0
    else
        return 1
    fi
}

if check_file "myfile.txt"; then
    echo "File exists"
fi

# Function arguments
function process() {
    local file="$1"
    local mode="${2:-default}"  # Second arg with default
    
    echo "Processing $file in $mode mode"
}
```

## Input/Output
```bash
# Read user input
read -p "Enter your name: " name
echo "Hello, $name"

# Read with timeout
if read -t 5 -p "Quick! Enter something: " input; then
    echo "You entered: $input"
else
    echo "Timeout!"
fi

# Redirect output
echo "Log message" > output.txt     # Overwrite
echo "More logs" >> output.txt      # Append

# Redirect stderr
command 2> error.log                # Stderr only
command > output.log 2>&1           # Both stdout and stderr
command &> combined.log             # Shorter syntax (both)

# Suppress output
command > /dev/null 2>&1            # Silent execution
```

## String Manipulation
```bash
text="Hello World"

# Length
echo "${#text}"  # 11

# Substring
echo "${text:0:5}"   # "Hello" (start:length)
echo "${text:6}"     # "World" (from position 6)

# Replace
echo "${text/World/Bash}"     # "Hello Bash" (first match)
echo "${text//o/0}"           # "Hell0 W0rld" (all matches)

# Remove prefix/suffix
filename="script.sh"
echo "${filename%.sh}"        # "script" (remove shortest suffix)
echo "${filename%.*}"         # "script" (remove any extension)

path="/usr/local/bin/script"
echo "${path##*/}"            # "script" (remove longest prefix - basename)
echo "${path%/*}"             # "/usr/local/bin" (remove suffix - dirname)
```

## Dangerous Commands (Always Double-Check!)
```bash
# rm - remove files
rm file.txt              # Delete file
rm -r directory/         # Delete directory recursively
rm -rf directory/        # Force delete (NO CONFIRMATION!)
# WARNING: 'rm -rf /' can delete your entire system!
# Always use absolute paths or confirm with 'ls' first

# mv - move/rename
mv file.txt /tmp/        # Move file
mv old.txt new.txt       # Rename file
mv -i file.txt /tmp/     # Interactive (confirm overwrite)

# cp - copy
cp file.txt backup.txt   # Copy file
cp -r dir1/ dir2/        # Copy directory recursively

# chmod - change permissions
chmod +x script.sh       # Make executable
chmod 644 file.txt       # rw-r--r-- (owner: read/write, others: read)
chmod 755 script.sh      # rwxr-xr-x (owner: all, others: read/execute)

# chown - change owner (usually needs sudo)
sudo chown user:group file.txt
```

## Common Patterns
```bash
# Check if command exists
if command -v git &> /dev/null; then
    echo "Git is installed"
fi

# Exit on missing dependency
command -v docker &> /dev/null || { echo "Docker not found"; exit 1; }

# Create directory if it doesn't exist
mkdir -p /path/to/directory  # -p: create parents, no error if exists

# Process all files matching pattern
find . -name "*.log" -type f | while read -r file; do
    echo "Processing $file"
done

# Trap (cleanup on exit)
trap 'echo "Cleaning up..."; rm -f /tmp/mytemp' EXIT
# Code runs even if script exits early

# Parse command-line options
while getopts "hvf:" opt; do
    case $opt in
        h) echo "Usage: $0 [-h] [-v] [-f file]"; exit 0 ;;
        v) verbose=true ;;
        f) file="$OPTARG" ;;
        *) exit 1 ;;
    esac
done
```

## Debugging
```bash
# Print debug info
echo "DEBUG: variable=$variable" >&2  # Print to stderr

# Check exit status
command
echo "Exit status: $?"

# Enable debug mode for section
set -x  # Turn on
command1
command2
set +x  # Turn off

# Verbose mode (print lines as executed)
bash -x script.sh
```

## Safety Checklist
```bash
# ✅ Always quote variables: "$var" not $var
# ✅ Use [[ ]] instead of [ ] (more features, safer)
# ✅ Use set -euo pipefail at script start
# ✅ Use 'local' for function variables
# ✅ Check command existence before using
# ✅ Use absolute paths or ./ for scripts
# ❌ Never parse ls output (use find or globs)
# ❌ Never use rm -rf with variables (typo = disaster)
```

**Why these patterns?** Bash is powerful but error-prone. Safety first!

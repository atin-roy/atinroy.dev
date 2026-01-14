#!/usr/bin/env bash
set -euo pipefail

if [ "$#" -lt 2 ]; then
  echo "Usage: $0 <project-path> <report-title>"
  exit 1
fi

project_path="$1"
shift
report_title="$*"
logs_dir="${project_path}/logs"
mkdir -p "$logs_dir"

existing_reports=("$logs_dir"/report-*.md)
count=0
for report in "${existing_reports[@]}"; do
  if [ -e "$report" ]; then
    count=$((count + 1))
  fi
done
next=$((count + 1))

slug=$(echo "${report_title}" | tr '[:upper:]' '[:lower:]' | tr -c 'a-z0-9' '-' | sed 's/-\{2,\}/-/g' | sed 's/^-//' | sed 's/-$//')
if [ -z "$slug" ]; then
  slug="untitled"
fi

report_file="${logs_dir}/report-${next}-${slug}.md"

cat <<EOF > "${report_file}"
# Report ${next} – ${report_title}

**Date:** $(date +%Y-%m-%d)  
**Severity:** medium

## What happened

## Fix

## What I learned

EOF

echo "${report_file}"

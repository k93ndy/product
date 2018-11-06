#!/bin/bash

echo "----------------------------------------"
if [ $# -ne 1 ]
then
  echo "args[1]:target template file with .tmpl suffix"
  exit 1
fi
echo "target template: ${1}"


function generate () {
# replace UnixTimeStamp field with current time stamp, then execute
    sed -e "s/{{ .UnixTimeStamp }}/`(date +%s)`/" ${1} | kubectl apply -f - 
}


project_root=$( cd $(dirname "${BASH_SOURCE[0]}") && cd .. && pwd -P )
cd ${project_root}

fileName=$(basename "${1}")
directoryName=$(dirname "${1}")
fileList=(`find ${directoryName} -name ${fileName}`)

for file in "${fileList[@]}"; do
  if [ "${file##*.}" = "tmpl" ]
  then
    echo "---"
    echo "target file: ${file}"
    generate ${file}
  else
    echo "support only .tmpl suffix"
  fi
done

echo "----------------------------------------"
exit 0
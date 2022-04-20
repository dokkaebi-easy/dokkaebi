#!/bin/bash

function gitlabClone() {
# git 저장소 clone
  git clone -b $1 --single-branch https://gitlab-ci-token:$2@$3
  echo "git clone Success!!!"
}

# git install
if [ -x "$(command -v git)" ]; then
    echo "Git already installed."
    # command
else
    sudo apt-get update
    sudo apt-get install git
    echo "Install git"
    # command
fi
# git clone
# java 에서 shell 호출하는 lib 로 args (파라미터) 를 보내서 현재 코드를 활용하는 방법을 염두하고 작성한 shell script
# aaaaaaaa, lab.ssafy.com/S06-final/S06.git 는 예시 매개변수 값
# 추후에 백에서 branch, gitlab_token와 repository_addr를 매개변수로 받아와야 함
gitlabClone "master" "aaaaaaaa" "lab.ssafy.com/S06-final/S06.git"
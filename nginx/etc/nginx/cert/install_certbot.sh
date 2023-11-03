#!/bin/bash
# https://www.inmotionhosting.com/support/website/ssl/lets-encrypt-ssl-ubuntu-with-certbot/
print_colored() {
  echo -e "\x1b[30;44m $1 \x1b[m"
}
print_colored "Install Certbot in Ubuntu with snapd"
print_colored "Install snapd"
sudo apt install snapd
print_colored "Ensure you have the latest snapd version installed"
sudo snap install core; sudo snap refresh core
print_colored "Install Certbot with snapd"
sudo snap install --classic certbot
print_colored "Create a symlink to ensure Certbot runs"
sudo ln -s /snap/bin/certbot /usr/bin/certbot
print_colored "Create an SSL Certificate with Certbot"
sudo certbot certonly --standalone -d jettyplus.jettytech.co.uk
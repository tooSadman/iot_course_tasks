*** Settings ***
Suite Setup       Open Browser    ${URL}    ${BROWSER}    # setup to open main page
Suite Teardown    Close Browser    # teardown to close the browser
Test Setup        Set Screenshot Directory    searchScreenshots
Library           SeleniumLibrary

*** Variables ***
${URL}            http://localhost
#${URL}            http://openncart.herokuapp.com/
${BROWSER}        chrome
${DEFAULT_TEXT}   blah-blah

*** Test Cases ***

Search For Cameras
    Maximize Browser Window
    Click Link    Cameras

Choose the Nikon camera
    Click Link    Nikon D300

Add the camera to the cart
    Clear Element Text    id:input-quantity
    Input Text    id:input-quantity    4
    Click Button    id:button-cart 
    
Confirm Order
    Click Element   css:div#cart button
    Click Element   partial link:Checkout
    Wait Until Element Is Visible   xpath=//*[@id="button-account"]
    Click Element   xpath=//*[@value="guest"]
    Sleep    1s
    Click Button    id:button-account
    Sleep    1s
    # Here we need to input all the Billing Details
    Input Text    id:input-payment-firstname    ${DEFAULT_TEXT}
    Input Text    id:input-payment-lastname    ${DEFAULT_TEXT}
    Input Text    id:input-payment-email    ${DEFAULT_TEXT}@gmaik.com
    Input Text    id:input-payment-telephone    ${DEFAULT_TEXT}
    Input Text    id:input-payment-address-1    ${DEFAULT_TEXT}
    Input Text    id:input-payment-city    ${DEFAULT_TEXT}
    Input Text    id:input-payment-postcode    79000
    Select From List By Value    id:input-payment-zone    3514
    Click Button    id:button-guest
    Wait Until Element Is Visible   xpath=//*[@id="button-shipping-method"]
    Click Button    id:button-shipping-method
    Wait Until Element Is Visible   xpath=//*[@id="collapse-payment-method"]/div/div[2]/div/input[1]
    Click Element   xpath=//*[@id="collapse-payment-method"]/div/div[2]/div/input[1]
    Click Button    id:button-payment-method
    Wait Until Element Is Visible   xpath=//*[@id="button-confirm"]
    Click Button    id:button-confirm
    Wait Until Page Contains Element   xpath=//*[@id="content"]/div/div/a
    Click Link    Continue
    Sleep   2s

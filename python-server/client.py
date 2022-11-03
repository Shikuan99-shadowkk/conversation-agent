import socket, time
import threading
import sys
from textblob import TextBlob
import sqlite3
import json
import collections

#Wait for incoming data from server
#.decode is used to turn the message in bytes to a string
def receive(socket, signal):
    while signal:
        try:
            data = socket.recv(1024)
            print(str(data.decode("utf-8")))
        except:
            print("You have been disconnected from the server")
            signal = False
            break

#Get host and port
# host = input("Host: ")
# port = int(input("Port: "))
host = "localhost"
port = 8081

#Attempt connection to server
try:
    client_socket = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
    client_socket.connect((host, port))
except:
    print("Could not make a connection to the server")
    input("Press enter to quit")
    sys.exit(0)

# #Create new thread to wait for data
# receiveThread = threading.Thread(target = receive, args = (client_socket, True))
# receiveThread.start()

def storePatient(data, client_id):
    user = json.loads(data)
    print("saving user: " + str(user))
    conn = sqlite3.connect('database/healthcare_database') 
    c = conn.cursor()
    # insert user as client
    c.execute('''
        INSERT INTO clients (client_id, client_name, age, sex, weight, height, exercise, temperature, pain_score, emotion_score)

            VALUES
            ({},"{}",{},"{}",{},{},{},{},{},{})
        '''.format(client_id, user['name'], user['age'], user['sex'], user['weight'], user['height'],
                    user['physicalActivity'], 
                    user['degree'], 
                    user['painLevel'], 
                    user['emotionLevel']))
    # fetch ids for main and minor symptoms in db
    c.execute('''
        SELECT
        symptom_id
        FROM symptoms
        WHERE symptom_name="{}"
        '''.format(user['mainSymptom']))
    main_symptom_id = c.fetchall()[0][0]
    c.execute('''
        SELECT
        symptom_id
        FROM symptoms
        WHERE symptom_name="{}"
        '''.format(user['minorSymptom']))
    minor_symptom_id = c.fetchall()[0][0]
    # insert relationship between symptoms and client
    c.execute('''
        INSERT INTO patients
        
        (patient_id, client_id, symptom_id, recommendation, start_time)

            VALUES
            (NULL,{},{},NULL,NULL)
        '''.format(client_id, main_symptom_id))
    c.execute('''
        INSERT INTO patients
        
        (patient_id, client_id, symptom_id, recommendation, start_time)

            VALUES
            (NULL,{},{},NULL,NULL)
        '''.format(client_id, minor_symptom_id))
    if user['allergy'] != 'null':
        # fetch id for allergen in db
        c.execute('''
        SELECT
        allergen_id
        FROM allergens
        WHERE allergen_name="{}"
        '''.format(user['allergy']))
        allergen_id = c.fetchall()[0][0]
        # insert relationship between allergens and client
        c.execute('''
        INSERT INTO allergies
        
        (allergy_id, client_id, allergen_id)

                VALUES
                (NULL,{},{})
        '''.format(client_id, allergen_id))
    conn.commit()
    client_socket.sendall(str.encode("patient saved\r\n"))
    
def getPatient(client_id):
    conn = sqlite3.connect('database/healthcare_database') 
    c = conn.cursor()
    c.execute('''
          SELECT
          *
          FROM clients
          WHERE client_id={}
          '''.format(client_id))
    
    client = c.fetchall()
    
    # check if client exists
    if len(client) == 0:
        return client_socket.sendall(str.encode("no patient\r\n"))
    
    # get allergies
    c.execute('''
            SELECT
            b.allergen_name
            FROM clients c
            LEFT JOIN allergies a ON c.client_id = a.client_id
            LEFT JOIN allergens b ON a.allergen_id = b.allergen_id
            WHERE c.client_id={}
            '''.format(client_id))
    allergy = c.fetchall()

    # get main symptom 
    c.execute('''
            SELECT
            s.symptom_name
            FROM clients c
            LEFT JOIN patients p ON c.client_id = p.client_id
            LEFT JOIN symptoms s ON p.symptom_id = s.symptom_id
            WHERE c.client_id={} AND s.symptom_type="main"
            '''.format(client_id))
    main_symptom = c.fetchall()

    # get minor symptom 
    c.execute('''
            SELECT
            s.symptom_name
            FROM clients c
            LEFT JOIN patients p ON c.client_id = p.client_id
            LEFT JOIN symptoms s ON p.symptom_id = s.symptom_id
            WHERE c.client_id={} AND s.symptom_type="minor"
            '''.format(client_id))
    minor_symptom = c.fetchall()

    objects_list = []
    for row in client:
        d = collections.OrderedDict()
        d["name"] = row[1]
        d["age"] = row[2]
        d["sex"] = row[3]
        d["weight"] = row[4]
        d["height"] = row[5]
        if row[6] == 1:
            d["physicalActivity"] = True
        else:
            d["physicalActivity"] = False
        d["degree"] = row[7]
        d["painLevel"] = row[8]
        d["emotionLevel"] = row[9]
        # returns list but in our case a singular value
        d["allergy"] = allergy[0][0]
        d["mainSymptom"] = main_symptom[0][0]
        d["minorSymptom"] = minor_symptom[0][0]
        objects_list.append(d)
    j = json.dumps(objects_list[0])
    print("retrieving user: " + j)
    client_socket.sendall(str.encode(str(j) + "\r\n"))
                        
    conn.commit()

#Send data to server
#str.encode is used to turn the string message into bytes so it can be sent across the network
while True:
    time.sleep(5)
    msg = client_socket.recv(1024)
    if not msg or '#' not in str(msg):
        continue
    msg = str(msg)
    split = msg.split('#')
    flag = split[0]
    data = split[1]
    data = data[:-5]
    client_id = 12
    if 'polarity' in flag:
        res = TextBlob(data)
        message = res.sentiment.polarity
        client_socket.sendall(str.encode(str(message) + "\r\n"))
    elif 'storePatient' in flag:
        storePatient(data, client_id)
    elif 'getPatient' in flag:
        getPatient(client_id)
    else:
        continue
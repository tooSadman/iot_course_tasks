# -*- coding: utf-8 -*-

class Book:

    total_number_of_pages = 0

    def __init__(self, title="50 shapes of grey", author="E. L. James", price=100, number_of_pages=200, genre="Cheap erotics"):
        self.title = title
        self.author = author
        self.price = price
        self.number_of_pages = number_of_pages
        self.genre = genre
        Book.total_number_of_pages += self.number_of_pages

    def to_string(self):                         #self полезен для того, чтобы обращаться к другим атрибутам класса: self=this
        print("Title: " + self.title + ", Author: " + str(self.author)
              + ", Price: " + str(self.price) + "$, Number of pages: "
              + str(self.number_of_pages) + " pages , Genre: " + str(self.genre))

    def print_sum(self):
        print("A Book called " + self.title + " has number of pages " + str(self.number_of_pages))

    @staticmethod
    def print_static_sum():
        print("Total number of pages of all Books = " + str(Book.total_number_of_pages))


if __name__ == "__main__":
    fifty_shapes = Book()
    potter = Book("Harry Potter", "Joanne Rowling", 80, 400, "Fantasy")
    decameron = Book("The Decameron", "Giovanni Boccaccio", 20, 200)
    fifty_shapes.to_string()
    potter.to_string()
    decameron.to_string()
    Book.print_static_sum()
    potter.print_sum()
    decameron.print_sum()

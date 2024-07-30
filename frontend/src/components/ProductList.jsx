import React, { useState, useEffect } from "react";
import { Box, Table, Thead, Tbody, Tr, Th, Td, Select, Button, Text, VStack, HStack } from "@chakra-ui/react";
import ProductItem from "./ProductItem";
import Pagination from "./Pagination";

const ProductList = () => {
  const [products, setProducts] = useState([]);
  const [currentPage, setCurrentPage] = useState(1);
  const [totalPages, setTotalPages] = useState(1);
  const [sort, setSort] = useState("price");

  useEffect(() => {
    fetchProducts();
  }, [currentPage, sort]);

  const fetchProducts = async () => {
    const response = await fetch(`http://localhost:8080/api/motherboards?page=${currentPage}`);
    const data = await response.json();

    setProducts(data.items); // Set products
    setTotalPages(data.totalPages); // Set total pages
  };

  return (
    <Box className="product-list" bg="var(--filter-bg)" borderRadius="8px" p="15px">
      <VStack align="start" spacing={4}>
        <HStack justify="space-between" w="full">
          <Text fontSize="2xl" color="var(--secondary-color)">
            Матични Плочи
          </Text>
          <HStack>
            <Text>Подреди по:</Text>
            <Select id="sort" value={sort} onChange={(e) => setSort(e.target.value)}>
              <option value="price">Цена (Најниска прво)</option>
              <option value="rating">Оценка (Највисока прво)</option>
              <option value="name">Име (А-Ш)</option>
            </Select>
          </HStack>
        </HStack>
        <Table variant="simple">
          <Thead>
            <Tr>
              <Th></Th>
              <Th>Име</Th>
              <Th>Лежиште</Th>
              <Th>Форм Фактор</Th>
              <Th>Меморија Поддржана</Th>
              <Th>Цена</Th>
              <Th></Th>
            </Tr>
          </Thead>
          <Tbody>
            {products.map((product) => (
              <ProductItem key={product.id} product={product} />
            ))}
          </Tbody>
        </Table>
        <Pagination currentPage={currentPage} totalPages={totalPages} onPageChange={setCurrentPage} />
      </VStack>
    </Box>
  );
};

export default ProductList;

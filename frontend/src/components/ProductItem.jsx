import React from "react";
import { Tr, Td, Button } from "@chakra-ui/react";

const ProductItem = ({ product }) => (
  <Tr>
    <Td>
      <img src={product.imageUrl} alt={product.name} width="50" height="50" />
    </Td>
    <Td>{product.name}</Td>
    <Td>{product.partModel.socket}</Td>
    <Td>{product.partModel.formFactor}</Td>
    <Td>{product.partModel.supportedMemory}</Td>
    <Td>{product.price} ден.</Td>
    <Td>
      <Button colorScheme="purple" as="a" href={product.productUrl}>
        Додади
      </Button>
    </Td>
  </Tr>
);

export default ProductItem;

import React from "react";
import { Tr, Td, Button } from "@chakra-ui/react";

const ProductItem = ({ product, partType }) => (
  <Tr>
    <Td>
      <img src={product.imageUrl} alt={product.name} width="50" height="50" />
    </Td>
    <Td>{product.name}</Td>
    {partType === "Motherboards" && (
      <>
        <Td>{product.partModel.socket}</Td>
        <Td>{product.partModel.formFactor}</Td>
        <Td>{product.partModel.supportedMemory}</Td>
      </>
    )}
    {partType === "RAM" && (
      <>
        <Td>{product.partModel.name}</Td>
        <Td>{product.partModel.capacity}</Td>
        <Td>{product.partModel.frequency}</Td>
      </>
    )}
    {partType === "Case" && (
      <>
        <Td>{product.partModel.name}</Td>
        <Td>{product.partModel.formFactor}</Td>
      </>
    )}
    {partType === "CPU Cooler" && (
      <>
        <Td>{product.partModel.name}</Td>
        <Td>{product.partModel.coolerType}</Td>
      </>
    )}
    {partType === "Storage" && (
      <>
        <Td>{product.partModel.name}</Td>
        <Td>{product.partModel.capacity}</Td>
        <Td>{product.partModel.formFactor}</Td>
      </>
    )}
    {partType === "PSU" && (
      <>
        <Td>{product.partModel.wattage} W</Td>
        <Td>80+ {product.partModel.efficiencyRating}</Td>
      </>
    )}
    {partType === "CPU" && (
      <>
        <Td>{product.partModel.name}</Td>
        <Td>{product.partModel.brand}</Td>
        <Td>{product.partModel.socket}</Td>
        <Td>{product.partModel.boostClock} MHz</Td>
        <Td>{product.partModel.tdp} W</Td>
      </>
    )}
    <Td>{product.price} ден.</Td>
    <Td>
      <Button colorScheme="purple" as="a" href={product.productUrl}>
        Додади
      </Button>
    </Td>
  </Tr>
);

export default ProductItem;

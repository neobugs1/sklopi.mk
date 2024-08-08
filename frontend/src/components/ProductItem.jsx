import React from "react";
import { Tr, Td, Button, Image, Link } from "@chakra-ui/react";
import anhoch from "../assets/lUuXIR1al8ZZVSTbX4e7Rryi6jgaymSLQGsDYjkT.svg";
import setec from "../assets/setec-belo-logo.png";
import { usePCBuild } from "./PCBuildProvider";

const ProductItem = ({ product, partType }) => {
  const { addComponent } = usePCBuild();

  const handleAddClick = () => {
    addComponent(product.id);
  };

  return (
    <Tr>
      <Td>
        <img src={product.imageUrl} alt={product.name} width="50" height="50" />
      </Td>
      <Td>{product.name}</Td>
      {/* Conditionally render part-specific columns */}
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
          <Td>{product.partModel.totalCores}</Td>
          <Td>{product.partModel.brand}</Td>
          <Td>{product.partModel.socket}</Td>
          <Td>{product.partModel.boostClock} MHz</Td>
          <Td>{product.partModel.tdp} W</Td>
        </>
      )}
      {partType === "GPU" && (
        <>
          <Td>{product.partModel.name}</Td>
          <Td>{product.partModel.brand}</Td>
          <Td>{product.partModel.memorySize} GB</Td>
          <Td>{parseInt(product.partModel.performanceScore) / parseInt(product.price)}</Td>
        </>
      )}

      <Td>
        {/* product url contains setec? */}
        {product.productUrl.includes("setec") ? (
          <Link href={product.productUrl} bg="transparent">
            <Image src={setec} />
          </Link>
        ) : (
          <Link href={product.productUrl} bg="transparent">
            <Image src={anhoch} />
          </Link>
        )}
      </Td>
      <Td>{product.price} ден.</Td>
      <Td>
        <Button colorScheme="purple" onClick={handleAddClick}>
          Додади
        </Button>
      </Td>
    </Tr>
  );
};

export default ProductItem;
